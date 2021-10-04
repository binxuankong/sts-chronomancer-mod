package chronoMod.cards;

import chronoMod.DefaultMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.JadePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static chronoMod.DefaultMod.makeCardPath;

public class BlackHole extends AbstractXCostCard {
    public static final String ID = DefaultMod.makeID(BlackHole.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;

    public BlackHole() {
        super(ID, IMG, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = this.getEffectNum(p);
        if (effect > 0) {
            if (m != null) {
                this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
            }
            this.addToBot(new WaitAction(0.8F));

            int total_damage = this.damage * effect;
            this.addToBot(new DamageAction(m, new DamageInfo(p, total_damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));
            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
            this.addToBot(new ApplyPowerAction(p, p, new JadePower(p, effect), effect));
        }
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
        return new BlackHole();
    }
}
