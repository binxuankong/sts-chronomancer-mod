package chronoMod.cards;

import chronoMod.DefaultMod;
import chronoMod.actions.ArcaneBarrageAction;
import chronoMod.actions.ModifyNumberHitsAction;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.JadePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.DefaultMod.makeCardPath;

public class ArcaneBarrage extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(ArcaneBarrage.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BASE_HITS = 2;

    public ArcaneBarrage() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = BASE_HITS;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 1; i <= this.magicNumber; i++) {
            this.addToBot(new ArcaneBarrageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void triggerWhenDrawn() {
        this.addToBot(new ModifyNumberHitsAction(this.uuid, 1));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new JadePower(AbstractDungeon.player, 1), 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ArcaneBarrage();
    }
}
