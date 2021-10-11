package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.MysticShiftPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static chronoMod.ChronoMod.makeCardPath;

public class MysticShift extends AbstractDynamicCard {
    public static final String ID = ChronoMod.makeID(MysticShift.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    public MysticShift() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new MysticShiftPower(p)));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new MysticShift();
    }
}
